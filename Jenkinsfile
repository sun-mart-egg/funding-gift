pipeline {
    agent any

    stages {
        stage("checkout") {
            steps {
                script {
                    git credentialsId: 'gitlab', url: 'https://lab.ssafy.com/s10-fintech-finance-sub2/S10P22D201.git', branch: "main"
                }
            }
        }
        
        stage('front_build'){
            steps{
                sh 'git checkout fe'
                sh 'docker stop fe'
                sh 'docker rm fe'
                sh 'docker rmi fe'
                sh 'docker build -t fe ./FE'
                sh 'docker run -d --name fe -p 5173:5173 fe'   
            }
        }

        stage('back_build'){
            steps{
                sh 'git checkout be'
                sh 'docker stop be'
                sh 'docker rm be'
                sh 'docker rmi be'
                sh 'docker build -t be ./BE'
                sh 'docker run -d --name be -p 8081:8081 be'   
            }
        }
        
    }
}
