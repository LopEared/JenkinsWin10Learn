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
				def myout = repository_link.substring(45,21)
				echo "<-------START OF MY CODE------>"
				
				def date = (new Date()).format("dd.MM.YYYY")
				commitMsg = "${ticketNumber} reporting SDMS PatchTesting NG 8 ${date} Build #${BUILD_NUMBER}"
				echo commitMsg
				echo powershell(returnStdout: true, script:"""

				$dirRepName = "$ReportRepository.Substring(45,21)" 
				md $dirRepName
				cd $dirRepName

				
				
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
