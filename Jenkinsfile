pipeline {
    agent any

    stages {
        stage("checkout_fe") {
            steps {
                script {
                    git credentialsId: 'gitlab', url: 'https://lab.ssafy.com/s10-fintech-finance-sub2/S10P22D201.git', branch: "fe"
                }
            }
        }
        
        stage('front_build'){
            steps{
                script {
                    def feRunning = sh(script: 'docker ps -a --filter "name=fe" --format "{{.Names}}"', returnStatus: true)
                    if (feRunning == 1) {
                        // fe container is running, stop and remove it
                        sh 'docker stop fe'
                        sh 'docker rm fe'
                        sh 'docker rmi fe'
                    }
                }
                
                sh 'docker build -t fe ./FE'
                sh 'docker run -d --name fe -p 5173:5173 fe'   
            }
        }

        stage("checkout_be") {
            steps {
                script {
                    git credentialsId: 'gitlab', url: 'https://lab.ssafy.com/s10-fintech-finance-sub2/S10P22D201.git', branch: "be"
                }
            }
        }

        stage('back_build'){
            steps{
                script {
                    def feRunning = sh(script: 'docker ps -a --filter "name=be" --format "{{.Names}}"', returnStatus: true)
                    if (feRunning == 1) {
                        // be container is running, stop and remove it
                        sh 'docker stop be'
                        sh 'docker rm be'
                        sh 'docker rmi be'
                    }
                }
                sh 'docker build -t be ./BE'
                sh 'docker run -d --name be -p 8081:8081 be'   
            }
        }
        
    }
}
