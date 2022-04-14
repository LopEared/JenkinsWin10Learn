#!/usr/bin/groovy

pipeline {
    agent any
	options{
		timestamps()
	}
	
    environment {
        PROJECT_NAME 		= "Neptun"
	    OWNER_NAME   		= "Denis Astahov"
		ReportRepository 	= 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git'
		BUILD_NUMBER        = "${env.BUILD_NUMBER}"
		JOB_NAME            = "${env.JOB_NAME}"
		NODE_NAME           = "${env.NODE_NAME}"
		WORKSPACE           = "${env.WORKSPACE}"
		ticketNumber 		= "INFLMS26356"
		appName				= "NG 8"
		ReportBranchName  	= "master"  
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
				echo "Repositoryy way is ${ReportRepository}"
				
				echo "<-------START OF MY CODE------>"
				
	
				
				echo powershell(returnStdout: true, script:"""

				md ${ReportRepository}
				cd ${ReportRepository}
				New-Item -Path . -Name "{$ReportBranchName}.txt" -ItemType "file" -Value "${ReportRepository}"
				dir
				
				""")

            }
        }

        stage('4-Celebrate') {
            steps {
                echo "CONGRATULYACIYA!"
            }
        }	
	}
}
