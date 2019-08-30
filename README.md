# Sat Azure Functions

UnzipInvoices function is triggered when is detected a new zip file which contains xml invoices, then it’s called an API that takes all the xml files then they are transformed to  Java object and finally those are presited in cosmos db

 
### Tech

Languages, Frameworks, tools:

* [.NET CORE] – Last version.
* [Azure Functions Core Tools] - Version 2.6.666 or above.
* [Azure CLI] – Last version.
* [Apache Maven] – Version 3.5.3  
* [JDK] – Version 1.8.0_221

### Installation

It’s required to have installed everything that is listed on the Tech section.

Install Azure Functions Core Tools using brew. For more means to install it, go forward to this link [link]( https://docs.microsoft.com/en-us/azure/azure-functions/functions-run-local)

```sh
$ brew tap azure/functions
$ brew install azure-functions-core-tools
```

Install Azure CLI using brew.

```sh
$ brew update && brew install azure-cli
```

Set the following variables in your .bash_profile file to get access on all the resources needed to run locally the azure functions:

```sh
export CONNECTION_STRING_MXDEVSATINVOICEBLOB=" "
export HOST_API="http://localhost:8080"
export INVOICE_MANAGEMENT_UPLOAD="/invoice-management/upload"
```

### Plugins

This project is currently using the following plugins. Instructions on how to use them in your own application are linked below.

| Plugin | Reference |
| ------ | ------ |
| azure-functions-maven-plugin| [Maven Plugin for Azure Functions]( https://docs.microsoft.com/en-us/java/api/overview/azure/maven/azure-functions-maven-plugin/readme?view=azure-java-stable) |
| maven-resources-plugin| [Apache Maven Resources Plugin]( https://maven.apache.org/plugins/maven-resources-plugin/) |
| maven-dependency-plugin | [Apache Maven Dependency Plugin]( https://maven.apache.org/plugins/maven-dependency-plugin/) |
| maven-clean-plugin| [Apache Maven Clean Plugin]( https://maven.apache.org/plugins/maven-clean-plugin/) |
### Development

to run locally, open your Terminal in the root of the project and run these commands.

First:
```sh
$ mvn -X clean package 
```

Second:
```sh
$ mvn -X azure-functions:run
```
#### Building for source

Create the resource on azure with the same name of the functionAppName property found in the pom file:

```sh
<functionAppName>name of the service in azure</functionAppName>
```

Deploy functions on azure:

First (login to get grant access):
```sh
$ az login
```
Second (set the account to be used):
```sh
$ az account set -s “Id or name of the subscription”
```
Third (construct the jar file):
```sh
$ mvn -X clean package 
```
Fourth (deploy functions on azure):
```sh
$ mvn -X azure-functions:deploy
```
