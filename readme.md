## How to run Crowd server
* Docker:
  https://hub.docker.com/r/alvistack/crowd
* Install:
  https://confluence.atlassian.com/crowd/installing-crowd-24248834.html

## How to configure openidserver on crowd (CrowdID)
* configure Crowd
* add generic application with name and password
* configure crowd.properties
* restart bundled Crowd server or standalone openidserver
* login to openidserver open administration and set whitelist 

## How to try
* run application with profile working to see my implementation of SReg attributes
* run application with profile not-working and uncomment dependency to spring-security-openid to see attributes not being fetched properly from crowd server
