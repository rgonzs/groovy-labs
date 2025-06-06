def call() {
    String response = new URL('https://api.github.com/repos/aquasecurity/trivy/releases/latest').getText()
    def versions = readJSON text: response
    Map actualVersion = versions.assets.find { item ->
        return item.name =~ /Linux-64bit.tar.gz/
    }
    String downloadUrl = actualVersion.browser_download_url
    String name = actualVersion.name
    sh """
    curl -LO ${downloadUrl}
    tar -xzf ${actualVersion.name}
    """
}
