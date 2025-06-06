def call(String image) {
    def response = httpRequest url: 'https://api.github.com/repos/aquasecurity/trivy/releases/latest', validResponseCodes: '200'
    def versions = readJSON text: response.content
    Map actualVersion = versions.assets.find { item ->
        return item.name =~ /Linux-64bit.tar.gz/
    }
    String downloadUrl = actualVersion.browser_download_url
    String name = actualVersion.name
    sh """
    curl -LO ${downloadUrl}
    tar -xzf ${actualVersion.name}
    ./trivy -q image --input ${image} --cache-dir /tmp/trivy-cache
    """
}
