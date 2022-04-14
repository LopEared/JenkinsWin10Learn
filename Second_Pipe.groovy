#!/usr/bin/groovy

import java.util.concurrent.TimeoutException
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException
import hudson.model.Slave.*
import hudson.slaves.OfflineCause.SimpleOfflineCause
import hudson.slaves.OfflineCause

properties([
    parameters([
        
        string(name: 'ReportRepository', defaultValue: 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git', description: 'Repository to store final reports', ),
        string(name: 'ReportBranchName', defaultValue: 'master', description: 'Repository branch to store final reports', ),
        string(name: 'appName', defaultValue: 'NG 8', description: 'Application name', ),
        string(name: 'appVersion', defaultValue: '8', description: 'Application version', ),
        string(name: 'ticketNumber', defaultValue: 'INFLMS-26356', description: 'Ticket number under which the commit will be pushed into Repo', ),
    ])
])


def mainPipe() {
        stage("Collecting Report info") {
			agent {
				any
			}

            def date = (new Date()).format("dd.MM.YYYY")
            commitMsg = "${ticketNumber} reporting SDMS PatchTesting NG 8 ${date} Build #${BUILD_NUMBER}"

            echo powershell(returnStdout: true, script:"""

            git clone -b "${ReportBranchName}" "${ReportRepository}"

            cd patch-testing-results

            if(!(Test-Path "${appName}")) {
                md "${appName}"
            }

            md "${appName}\\${date}\\${BUILD_NUMBER}"
            dir
            """)
        }
             
    }

timestamps {
    try {
        mainPipe()
    }
    catch(FlowInterruptedException interruptEx){
      throw interruptEx
    }
    finally {
        echo "THis is the END"
    }
}