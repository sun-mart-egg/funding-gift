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
        
    }
}
