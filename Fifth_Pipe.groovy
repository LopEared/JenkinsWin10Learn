#!/usr/bin/groovy
import java.util.concurrent.TimeoutException
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException
import hudson.model.Slave.*
import hudson.slaves.OfflineCause.SimpleOfflineCause
import hudson.slaves.OfflineCause

properties([
    parameters([
        //Patch Testing (PT) params
        booleanParam(name: 'isParallelRun', defaultValue: true, description: 'If TRUE - machines will be created simultaneously. Otherwise, client creation will start after the server is created.'),
        booleanParam(name: 'runTests', defaultValue: true, description: 'If true - run test suites on VM'),
        booleanParam(name: 'buildReport', defaultValue: true, description: 'If true - report files will be created'),
        booleanParam(name: 'uploadReportToGit', defaultValue: true, description: 'If true - report.pdf will be uploaded to ReportRepository'),
        booleanParam(name: 'stopOnFinish', defaultValue: true, description: 'If true - powerOff VM after Testing'),
        //Test agent params
        booleanParam(name: 'isExistingAgentMachine', defaultValue: false, description: 'If true - all prerequisites will not be executed'),
        booleanParam(name: 'deleteAgentMachineAfterFinishing', defaultValue: false, description: 'If true: created test agent will be deleted after execution will have finished'),
        booleanParam(name: 'installWindowsPatchOnAgent', defaultValue: true, description: 'If true: latest windows patches will be applied to the Test agent'),
        string(name: 'agentTemplateName', defaultValue: 'SDMS_NG8_Client_PatchTesting', description: 'Test agent template name', ),
        string(name: 'agentTemplateSnapshot', defaultValue: 'NG8_SDMS_CLIENT', description: 'Snapshot name from test agent template', ),
        string(name: 'agentMachineName', defaultValue: '', description: 'Machine will be created with provided name (If empty then machine will be created with default name). Please use a name up to 15 characters with no spaces.', ),
        //Server params
        booleanParam(name: 'isExistingServerMachine', defaultValue: false, description: 'If false - new APP Server will be created'),
        booleanParam(name: 'deleteServerAfterFinishing', defaultValue: false, description: 'If true: created App Server will be deleted after execution will have finished'),
        booleanParam(name: 'installWindowsPatchOnServer', defaultValue: true, description: 'If true: latest windows patches will be applied to the App Server'),
        booleanParam(name: 'installOraclePatchOnServer', defaultValue: true, description: 'If true: latest oracle patches will be applied to the Oracle DB'),
        string(name: 'serverTemplateName', defaultValue: 'SDMS_NG8_Server_PatchTesting', description: 'Server template name', ),
        string(name: 'serverTemplateSnapshot', defaultValue: 'NG8_SDMS_SR', description: 'Snapshot name from server template', ),
        string(name: 'serverName', defaultValue: '', description: 'server name(If empty then machine will be created with default name)', ),
        //PT prereqs
        string(name: 'testFolder', defaultValue: 'NGPatchTesting', description: 'vSphere folder where test VMs will be stored', ),
        string(name: 'psScriptsRepo', defaultValue: 'https://edm:edmwaters@code.waters.com/bitbucket/scm/infnug/infrastructure.git', description: 'Repository to take Powershell scripts from', ),        
        string(name: 'psScriptsBranch', defaultValue: 'feature/INFLMS-22022-nugenesis-8-sr2-patch-testing', description: 'Repository to take Powershell scripts from', ),  
        string(name: 'ReportRepository', defaultValue: 'https://code.waters.com/bitbucket/scm/infnsd/patch-testing-results.git', description: 'Repository to store final reports', ),
        string(name: 'ReportBranchName', defaultValue: 'master', description: 'Repository branch to store final reports', ),
        string(name: 'PT_Branch', defaultValue: 'feature/INFLMS-22022-nugenesis-8-sr2-patch-testing', description: 'Branch from Infrastructure repo to checkout and get scripts', ),
        string(name: 'netOpatchLocation', defaultValue: '\\\\xeon4\\NuGenesis\\SDMS\\AUTOMATION\\PatchTesting\\Oracle\\OraclePatchUtility', description: '', ),
        string(name: 'netOraPatchLocation', defaultValue: '\\\\xeon4\\NuGenesis\\SDMS\\AUTOMATION\\PatchTesting\\Oracle\\Ora11.2.0.4.2', description: '', ),
        string(name: 'timeout', defaultValue: '1000', description: 'timeout for execution prerequisites and running tests (In Minutes)', ),
		//Testnig params
        booleanParam(name: 'debugMode', defaultValue: true, description: "Debug mode value. 'false' uses for official testing"),
        choice(name: 'runMode', choices: ['CSMode & WebMode', 'CSMode', 'WebMode'], description: 'Kind of Toolkit tests'),
        choice(name: 'clonedMachineType', choices: ['SDMS_Smoke',
                                                   'SDMS_Data_Retention',
                                                   'SDMS_Legal_Hold',
                                                   'SDMS_Old_Audit_Trail',
                                                   'SDMS_Print_Capture',
                                                   'SDMS_Administrator',
                                                   'SDMS_Locale',
                                                   'SDMS_FileCapture_AAFFJ',
                                                   'SDMS_FileCapture_Empower',
                                                   'SDMS_FileCapture_Generic',
                                                   'SDMS_FileCapture_MassLynx',
                                                   'SDMS_FileCapture_Office',
                                                   'SDMS_Toolkit',
                                                   'SDMS_Setup',
                                                   'SDMS_New_Audit_Trail'], description: 'Clone machine type'),
        string(name: 'secondUnifyName', defaultValue: 'Printer2_THISISTHEENGLISHSTRING', description: '', ),
        string(name: 'appName', defaultValue: 'NG 8', description: 'Application name', ),
        string(name: 'appVersion', defaultValue: '8', description: 'Application version', ),
        string(name: 'installationType', defaultValue: 'Client', description: 'Installation Type', ),
        string(name: 'performerName', defaultValue: 'Jenkins Performer', description: 'Performer name', ),
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
    
    stage('Print text'){
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
