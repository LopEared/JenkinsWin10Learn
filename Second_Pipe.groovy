pipeline {
    agent any
	options{
		timestamps()
	}
	
    environment {
        PROJECT_NAME = "Neptun"
	    OWNER_NAME   = "Denis Astahov"
		repository_link = 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git'
		BUILD_NUMBER        = "${env.BUILD_NUMBER}"
		JOB_NAME            = "${env.JOB_NAME}"
		NODE_NAME           = "${env.NODE_NAME}"
		WORKSPACE           = "${env.WORKSPACE}"
    }

    stages {
        stage('1-Build') {
            steps {
                echo "Start of Stage Build..."
				echo "Building......."
				echo "End of Stage Build..."
            }
        }
        stage('2-Test') {
            steps {
                echo "Start of Stage Test..."
				echo "Testing......."
				echo "Privet ${PROJECT_NAME}"
				echo "Owner is ${OWNER_NAME}"
				echo "Repositoryy way is ${repository_link}"
				echo "End of Stage Build..."
				def repFolder = ${repository_link}
				repFolder = repFolder.substring(45,21)
				echo "Name of repository folder is:  ${repFolder}"
            }
        }

        stage('4-Celebrate') {
            steps {
                echo "CONGRATULYACIYA!"
            }
        }	
	}
}
