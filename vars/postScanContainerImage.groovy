def call(){
    sh script: "rm -rf trivy*", label: "Cleaning trivy installation"
}