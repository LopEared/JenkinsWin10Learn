#!/usr/bin/groovy
import java.util.concurrent.TimeoutException
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException
import hudson.model.Slave.*
import hudson.slaves.OfflineCause.SimpleOfflineCause
import hudson.slaves.OfflineCause

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
            
            ws("C:\\mike_builder\\Git") {
                def monthYear = (new Date()).format("MM.YYYY")
                def date = (new Date()).format("dd.MM.YYYY")
                echo powershell(returnStdout: true, script:"""
                md "${monthYear}\\${date}\\${BUILD_NUMBER}"
                md "${appName}\\${monthYear}\\${date}\\${BUILD_NUMBER}"
		cd "${appName}\\${monthYear}\\${date}\\${BUILD_NUMBER}"
		pwd
		Copy-Item -Path "C:\\test_file.txt" -Destination "C:\\mike_builder\\Git\\${appName}\\${monthYear}\\${date}\\${BUILD_NUMBER}"
                dir
		""")   
            }
        }
    }
}

timestamps {
        mainPipe()
        printEdge()
        salaam()
        printEdge()
        myPath()
        printEdge()
        specificDate()
}
