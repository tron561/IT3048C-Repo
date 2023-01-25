# App Name - IT3048C
Vyviene Spaulding, Alex Hedges, Francesca Suba
## Introduction
intro heree
## Storyboard/Wireframe
![App Wireframe](https://github.com/francescasuba/OnTrack/blob/51d2663064a31c8414e6fd7cecd241bf2d00d953/storyboard.png)
## Functional Requirements
### Requirement 1
#### Scenario
As an OnTrack user, I want to utilize a task manager application so that I can track my weekly tasks. 
#### Examples:
##### 1.1
- Given: a list of tasks 
- When: I search for a given task that is there 
- Then: I should receive at least one result for that keyword 
##### 1.2
- Given: a list of tasks 
- When: I search for a task that is not there 
- Then: I should receive a message that tells me the task is not found 
##### 1.3
- Given: a list of tasks 
- When: I sort by due date 
- Then: the tasks should be sorted in order by due date with the tasks due the soonest at the top 
##### 1.4
- Given: a list of tasks 
- When: I filter by completion 
- Then: I should see only tasks that have not been completed 
##### 1.5
- Given: a list of tasks 
- When: I click or tap on a task 
- Then: I should be brought to a page that shows me more information about that task 
### Requirement 2
#### Scenario
As an OnTrack user, I want to be able to log into the app so that I can access my account and my tasks.
#### Examples:
#### 2.1
- Given: the option to log-in is available
- When: I log in
- Then: I should be able to access all of the tasks I've created in the past
### Requirement 3
#### Scenario
As an OnTrack user, I want to be able to search for task names and keywords so that I can access the specific tasks I want quickly.
#### Examples:
#### 3.1
- Given: tasks that contain the keyword 'Assignment' exist
- When: I search the word 'Assignment'
- Then: I receive a list of all my tasks that contain the word 'Assignment'
#### 3.2
- Given: tasks that contain the keyword 'Exam' do NOT exist
- When: I search the word 'Exam'
- Then: I receive an error message explaining that no existing tasks contain the word 'Exam'

## Class Diagram
![OnTrack Class Diagram](https://github.com/francescasuba/OnTrack/blob/78ccd194ab1f1cda02d203638572429308269c1f/OnTrack%20Class%20Diagram.png)

**com.ontrack:** contains user interface and controller. The UI consists of a few elements such as the main tasks kanban board and an add tasks dialog. The controller includes the index() operation to draw up the starting page of the application.

**com.ontrack.service:** contains the business logic for the application. Includes an interface that contains a couple of operations such as getting a task by name and adding a task. Also includes the service implementation class and a stub to be used as a hardcoded representation of the interface during devlopment.

**com.ontrack.dto:** contains noun class Task with several attributes.

**com.ontack.dao:** contains an interface that handles interactions with a persistence mechanism. Also includes the DAO implementation class and a stub to be used as a hardcoded representation of the interface during devlopment.

## Product and Sprint Backlogs
Located under 'Projects' tab on github repo.
https://github.com/users/francescasuba/projects/3

## Team Roles
- Product Owner:
- Business Logic and Persistence:
- UI:
