#!/usr/bin/groovy

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
