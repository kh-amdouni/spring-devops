pipeline {
    agent any
    stages {
        stage('Set Java Version') {
            steps {
                script {
                    tool name: 'JAVA_HOME', type: 'jdk'
                }
            }
        }

//****************** BUILD BACKEND - SPRINGBOOT **************************
        stage('Checkout Backend Repo') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: '*/master']],
                        userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/Spring-Devops']]
                    ])
                }
            }
        }

        stage('BUILD Backend- INSTALL') {
            steps {
                withEnv(["JAVA_HOME=${tool name: 'JAVA_HOME', type: 'jdk'}"]) {
                    sh 'mvn clean install'
                }
            }
        }
        
        // stage('COMPILE Backend') {
        //     steps {
        //         sh 'mvn compile'
        //     }
        // }


//************************************* BUILD FRONTEND - ANGULAR ***************************
                stage('Checkout Frontend Repo') {
                    steps {
                        script {
                            checkout([
                                $class: 'GitSCM',
                                branches: [[name: 'master']],
                                userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/front-devops.git']]
                            ])
                        }
                    }
                }

                stage('Build Frontend') {
                    steps {
                        sh 'npm install'
                        sh 'npm run ng build'
                    }
                }

//******************************** DOCKER BUILD AND PUSH IMAGES **************************
                    //******************************** DOCKER BUILD AND PUSH BACKEND - SPRINGBOOT :latest  IMAGE
            stage('Build and Push Backend Image') {
                steps {
                    script {
                        // Add the Git checkout step for the backend repository here
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/master']],
                            userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/Spring-Devops']]
                        ])
                        
                        // Authenticate with Docker Hub using credentials
                        withCredentials([string(credentialsId: 'Docker', variable: 'password')]) {
                            sh "docker login -u azzakouka -p azzaesprit159"
                        }
            
                          // Build the backend Docker image
                            def backendImage = docker.build('azzakouka/spring-app', '-f /var/lib/jenkins/workspace/Devops/Dockerfile .')
                            
                            // Push the Docker image
                            backendImage.push()
                        }
                    }
                }


        //       stage('Deploy to Nexus Repository') {
        //     steps {
        //       script {
        //                 // Add the Git checkout step for the backend repository here
        //                 checkout([
        //                     $class: 'GitSCM',
        //                     branches: [[name: '*/master']],
        //                     userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/Spring-Devops']]
        //                 ])
                        
        //                 // Authenticate with Docker Hub using credentials
        //                withCredentials([usernamePassword(credentialsId: 'nexus3', passwordVariable: 'pwd', usernameVariable: 'name')]) {
        //                      withEnv(["JAVA_HOME=${tool name: 'JAVA_HOME', type: 'jdk'}"]) {
        //         sh "mvn deploy -s /usr/share/maven/conf/settings.xml -Dusername=\$name -Dpassword=\$pwd"
        //     }
        //     }
        //    }
        //   }
        // }

          //******************************** DOCKER BUILD AND PUSH FRONTEND - ANGULAR :frontend  IMAGE **********

        stage('Build and Push Frontend Image') {
    steps {
        script {
            // Add the Git checkout step for the backend repository here
            checkout([
                $class: 'GitSCM',
                branches: [[name: '*/master']],
                userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/front-devops']]
            ])
            
            // Authenticate with Docker Hub using credentials
            withCredentials([string(credentialsId: 'Docker', variable: 'password')]) {
                sh "docker login -u azzakouka -p azzaesprit159"
            }
            
            // Build the backend Docker image
            def backendImage = docker.build('azzakouka/devops:frontend', '-f Dockerfile .')
            
            // Push the Docker image
            backendImage.push()
        }
    }
}

//*********************** DOCKER-COMPOSE ****************

// stage('Run Docker Compose') {
//     steps {
//         script {
//             checkout([
//                 $class: 'GitSCM',
//                 branches: [[name: '*/master']], 
//                 userRemoteConfigs: [[url: 'https://github.com/KoukaAzza/Spring-Devops']]
//             ])

//             // Run the docker-compose command
//             sh 'docker compose up -d' 
//         }
//     }
// }

        // stage('Run Docker Compose') {
        //   steps {
        //      script {
        //     checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'Docker', url: 'https://github.com/KoukaAzza/Spring-Devops']])
        //     sh 'docker compose up -d' 
        //       }
        //     }
        //  }
        
//********************* SOANRQUBE ANALYSIS **********************
        //   stage("SonarQube analysis") {
        //     agent any
        //     steps {
        //         withSonarQubeEnv('sonarQube') {
        //             sh 'mvn sonar:sonar'
        //         }
        //     }
        // }


 
//******************************* SENDING EMAIL - Success while Build pipeline Success / Failure while Build pipeline fails
}
    
    post {
        success {
            mail to: 'azza.kouka@esprit.tn',
            subject: 'Jenkins Build pipeline: Success',
            body: '''Your pipeline build success.
                Build and push to Docker Hub successful.
                Thank you, go and check it
                Azza KOUKA'''
        }
        failure {
            mail to: 'azza.kouka@esprit.tn',
            subject: 'Jenkins Build pipeline: Failure',
            body: '''Your pipeline build failed.
                Build or push to Docker Hub failed.
                Thank you, please check
                Azza KOUKA'''
        }
    }
}
