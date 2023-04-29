# STC Assessment

## Folder structure
1 - problem solving

2 - sql

3 - system design spring boot

## Information about the Application


Create a spring boot Docker application with Jre8

Consider design a system for managing upload/download files, with the below criteria
1. The application should be running on docker, Dockerfile or dockercompse.yml should be
   attached with the project.
2. Database should be provided as a docker file, and application should connect to it while
   starting as a docker container.
3. This application should be uploaded to GitHub. (Should be done completely)
   System should be managed as a tree data structure, space as the parent then folders and files as
   children.
   Consider design database using JPA entities as below: (Should be done completely)
   • Item table with type column (Space – Folder - File), to hold item meta data, expected
   columns (id, type, name, permission_group_id)
   • Files table to hold binary files, expected columns (id, binary, item_id)
   • Permission groups table, expected columns (id, group_name), one group can be
   assigned to many items.
   • Permissions table, expected columns (id, user_email, permission_level, group_id)
   Consider API implementation as below: (Only clean coding, API restful design, design patterns)
1. API to create space called “stc-assessments”, and assign a permission group like admin
   group, containing a user with VIEW access and another one with EDIT access.
2. API to create folder under “stc-assessments” space, called “backend”, making sure that
   the user has EDIT access on this space, blocking user that has VIEW access.
3. API to create file under “backend” folder, called “assessment.pdf”, making sure that the
   user has EDIT access on this folder, blocking user that has VIEW access.5
4. API to view file metadata, making sure that the user has access on this file, using native
   SQL query. (Making this as a graphql query will give you bonus point)
5. API to download file as a binary, making sure that the user has access on this file (Bonus
   API)
# How to run
- Setup Docker environment on your machine following the steps on the official documentation https://docs.docker.com/get-docker/
- install JDK 8 and Maven and add both to your environments variables
- download the project from the repository as a Zip and unZip it
- open terminal Or cmd and go to the project root location
- run " mvnw clean package -DskipTests " without quotes using cmd
- run " docker-compose up --build" without quotes using cmd

# Accessing the Application
- you can access the postgre DB (pgAdmin) throw http://localhost:5432/
- DB Name : stc-db
- DB Username : postgres
- DB Password : P@ssw0rd
- you can access the Application throw http://localhost:8080/stc/swagger-ui/index.html

# Provided Apis
- http://localhost:8080/stc/swagger-ui/index.html

# Important Notes
- application have only two permission groups
   - (1,ADMIN)
   - (2,USER)
- application have only two users attached to group ADMIN
   - (view@gmail.com, has VIEW access)
   - (edit@gmail.com, has EDIT access)