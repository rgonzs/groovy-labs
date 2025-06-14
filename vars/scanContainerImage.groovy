def call(String image) {
    def response = httpRequest url: 'https://api.github.com/repos/aquasecurity/trivy/releases/latest', validResponseCodes: '200'
    def versions = readJSON text: response.content
    Map actualVersion = versions.assets.find { item ->
        return item.name =~ /Linux-64bit.tar.gz/
    }
    String downloadUrl = actualVersion.browser_download_url
    def downloadTrivy = httpRequest url: downloadUrl, outputFile: "/tmp/${actualVersion.name}"
    sh script: """
    # curl -LO $downloadUrl
    mkdir -p `pwd`/trivy
    tar -xzf /tmp/${actualVersion.name} -C `pwd`/trivy
    """, label: 'Install trivy'
    sh script: "./trivy/trivy -q image --input ${image} --cache-dir /tmp/trivy-cache", label: 'Scanning image'
}
