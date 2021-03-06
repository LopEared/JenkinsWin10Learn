#!groovy DSL
// Hello! Michael!!!

pipeline {
  
	agent any
	
	options{
		timestamps()
	}
	
	environment { 
		CC = 'clang'
		repository_link = 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git'
		BUILD_NUMBER        = "${env.BUILD_NUMBER}"
		JOB_NAME            = "${env.JOB_NAME}"
		NODE_NAME           = "${env.NODE_NAME}"
		WORKSPACE           = "${env.WORKSPACE}"
	}
	
	stages {
		stage('My-JenkinsBuild-Steps') {
			steps {
				echo "Start"
				echo "Doing something.."
				echo "End"
				
				echo powershell(returnStdout: true, script: """ 
					Write-Output "PowerShell is mighty!"
				""")
				
				echo ${repository_link}
			}
		}
	}
}