pipeline {
    agent any
    environment {
		PROJECT_NAME = "Neptun"
		OWNER_NAME   = "Denis Astahov"
		BUILD_NUMBER        = "${env.BUILD_NUMBER}"
		JOB_NAME            = "${env.JOB_NAME}"
		NODE_NAME           = "${env.NODE_NAME}"
		WORKSPACE           = "${env.WORKSPACE}"
    }

    stages {
        stage('1-Build') {
            steps {
                echo "This is pipe build number: $BUILD_NUMBER"
				echo "This is pipe name: $JOB_NAME"
				echo "This is workspace place: $WORKSPACE"
            }
        }
        stage('2-Test') {
            steps {
                echo "Start of Stage Test..."
				echo "Testing......."
				echo "Privet ${PROJECT_NAME}"
				echo "Owner is ${OWNER_NAME}"
				echo "End of Stage Build..."
				echo "<---------------Start of my code------------------>"
				
				echo powershell(returnStdout: true, script:"""
					Write-Output "Hello, Mike! PowerShell is big POWER!!!"
				
				""")
				
				echo "<----------------End of my code------------------->"
            }
        }

        stage('4-Celebrate') {
            steps {
                echo "CONGRATULYACIYA!"
            }
        }	
	}
}