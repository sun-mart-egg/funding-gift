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
                    def feRunning = sh(script: 'docker ps -a --filter "name=fe" --format "{{.Names}}"', returnStdout: true).trim()
                    sh 'echo ${feRunning}'
                    if (feRunning) {
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
                    def beRunning = sh(script: 'docker ps -a --filter "name=be" --format "{{.Names}}"', returnStdout: true).trim()
                    sh 'echo ${beRunning}'
                    if (beRunning) {
                        // be container is running, stop and remove it
                        sh 'docker stop be'
                        sh 'docker rm be'
                        sh 'docker rmi be'
                    }

                    sh 'docker build -t be ./BE'
                    def dockerCmd = "docker run -e DB_PASSWORD=${env.DB_PASSWORD} -e DB_URL=${env.DB_URL} -e DB_USERNAME=${env.DB_USERNAME} -e EC2_PUBLIC_IP=${env.EC2_PUBLIC_IP} -e JWT_SECRET=${env.JWT_SECRET} -e KAKAO_CLIENT_ID=${env.KAKAO_CLIENT_ID} -e KAKAO_CLIENT_SECRET=${env.KAKAO_CLIENT_SECRET} -e REDIS_PASSWORD=${env.REDIS_PASSWORD} -e REDIS_PORT=${env.REDIS_PORT} -e BASE_URL=${env.BASE_URL} -e S3_ACCESS_KEY=${env.S3_ACCESS_KEY} -e S3_BUCKET_NAME=${env.S3_BUCKET_NAME} -e S3_SECRET_KEY=${env.S3_SECRET_KEY} -d --name be -p 8081:8081 be"
                    sh dockerCmd 
                }
            }
        }
        
    }
}
