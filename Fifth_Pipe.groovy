#!/usr/bin/groovy

properties([
    parameters([
        //Patch Testing (PT) params
        booleanParam(name: 'buildReport', defaultValue: true, description: 'If true - report files will be created'),
        booleanParam(name: 'uploadReportToGit', defaultValue: true, description: 'If true - report.pdf will be uploaded to ReportRepository'),
        string(name: 'ReportRepository', defaultValue: 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git', description: 'Repository to store final reports', ),
        string(name: 'ReportBranchName', defaultValue: 'master', description: 'Repository branch to store final reports', ),
        string(name: 'appName', defaultValue: 'NG 8', description: 'Application name', ),
        string(name: 'appVersion', defaultValue: '8', description: 'Application version', ),
        string(name: 'ticketNumber', defaultValue: 'INFLMS-26356', description: 'Ticket number under which the commit will be pushed into Repo', ),
    ])
])

def mainPipe() {  
  stage('Print text'){
        node("master"){
            echo "This is my first  comment in groovy-lesson!"
            echo powershell(returnStdout: true, script:"""
            date
            """)
        }
    }
}

def salaam() {
    println("Hello!");
}

def myPath() {
    def path = "Hello, Mikki! THis is the way!!!";
    println(path);
}

def printEdge() {
    echo "<-------------------edge between functions------------------------->"
}
def CheckPrinter() {
	echo "<-------------------!!!!!CHECK PRINTER!!!!!------------------------->"
	stage('Check printer settings'){
		node("master"){
			echo powershell(returnStdout: true, script:"""
				echo "Start powershell"
				echo "Disable Windows smartScreen scan"
				new-itemproperty "HKLM:\\Software\\Policies\\Microsoft\\Windows\\System" -Name EnableSmartScreen -Value 0 -Type DWORD -Force
				new-itemproperty "HKLM:\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\" -Name SmartScreenEnabled -Value Off -Type String -Force
				echo "Disable windows Firewall"
				Set-NetFirewallProfile -Profile Domain,Public,Private -Enabled False
				echo "Disable Windows Defender "
				Set-MpPreference -DisableRealtimeMonitoring \$true
				echo "Set print NuGenesis UNIFY 8.0 as Default printer"
				\$MYPRINTER = "NuGenesis UNIFY 8.0" 
				\$PRINTERTMP = (Get-CimInstance -ClassName CIM_Printer | WHERE {\$_.Name -eq \$MYPRINTER}[0])
				\$PRINTERTMP | Invoke-CimMethod -MethodName SetDefaultPrinter | Out-Host
				
				echo "Check Default printer:"
                   		
				\$DefaultPrinter = (Get-CimInstance -ClassName CIM_Printer | WHERE {\$_.Default -eq \$True} | Format-Table -AutoSize)
				echo \$DefaultPrinter
			""")
		}
	}
}


def specificDate() {
    // println(monthYear);
    // printEdge();
    // println(date);
    
    stage('Training stage'){
        node("master"){
            echo "This is my first  comment in groovy-lesson!"
            echo powershell(returnStdout: true, script:"""
            date
            """)
            
            ws("C:\\mike_builder\\Git\\") {
                def monthYear = (new Date()).format("MM.YYYY");
                def date = (new Date()).format("dd.MM.YYYY");
		def repoFolder = "${ReportRepository}".substring(45,66);
		echo "This is output for my variable:"
		echo repoFolder
		printEdge()
		echo powershell(returnStdout: true, script:"""
                echo "$ReportRepository".Substring(45,21)
		echo "<----->"
		echo "${repoFolder}"
		md "patch-testing-results"
		cd "${repoFolder}"
		pwd
		
		md "${appName}\\${repoFolder}\\${monthYear}\\${date}\\${BUILD_NUMBER}"
		pwd
		Copy-Item -Path "C:\\mike_builder\\Git\\testfile.txt" -Destination "C:\\mike_builder\\Git\\${appName}"
		
		echo "Check Default printer:"
                Get-CimInstance -ClassName CIM_Printer | WHERE {\$_.Default -eq \$True} | Format-Table -AutoSize
		
		""")      
 
            }
        }
    }
}

timestamps {
	CheckPrinter()
}
