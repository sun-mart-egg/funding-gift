pipeline {
    agent any

    tools {
        gradle "gradle"
        nodejs "nodejs"
    }

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
                dir('FE'){
                    sh 'npm install'
                    sh 'npm run dev'
                }
            }
        }
        
    }
}
