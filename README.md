# NewsNow - IT3048C
Vyviene Spaulding, Alex Hedges, Francesca Suba
## Introduction
Inspired by the spontaneous app sensation, BeReal, NewsNow sends a singular, daily notification. It is sent at a different time each day and will prompt the user to read an article (posted within 24 hours of the notification) from a reputable source. NewsNow will track the user's "ReadStreak" with a counter, which signifies how many days in a row the user has stayed up to date on current events. The user will also have an option to refresh the page for a new article if they wish to keep reading. NewsNow's mission is to encourage users to keep track of what is happening around the nation, one article, one day at a time.

## Storyboard/Wireframe
<img src="https://github.com/tron561/IT3048C-Repo/blob/main/wireframe.png" alt="NewsNow Wireframe" width="500">

## Functional Requirements
### Requirement 1
#### Scenario
As a NewsNow user, I want to use a news platform so that I can read daily articles and earn points.
#### Examples:
##### 1.1
- Given: A feed of news articles is availible
- When: I open the app
- Then: I should recieve a new article to read for the day
##### 1.2
- Given: A feed of news articles is availible 
- When: I open up a new article
- Then: I should recieve a flame point
##### 1.3
- Given: A feed of news articles is availible 
- When: I scroll down on the app
- Then: The page should be refreshed and a new article should display
### Requirement 2
#### Scenario
As a NewsNow user, I want to be able to log in to my account to keep track of my streak of daily articles
#### Examples:
##### 2.1
- Given: The option to log in is availible  
- When: I claim a flame point
- Then: I should be prompted to log in 
##### 2.2
- Given: The option to log in is availible
- When: I log in
- Then: I should be able to see my ReadStreak and my account information

## Class Diagram
<img src="https://github.com/tron561/IT3048C-Repo/blob/main/class_diagram.png" alt="NewsNow Class Diagram" width="500">

#### Main
- Main screen that the end-user will see. Will display main news feed, streak counter, and account.

#### StreakCount
- Number of consecutive days an end-user has opened the app to view the news.

#### AccountName
- Google account of the end-user.

#### NewsFeed
- Main news feed that displays content itself, location of content, author and datetime that content was published. 

#### News
- Noun class that represents news.

#### Account
- Noun class that represents account. 

#### Streak
- Noun class that represents streak.

#### NewsDataDAO
- Interface to pull news data from [newsdata.io](https://newsdata.io/)

#### GooglePlaySSODAO
- Interface for Google Play Services Signle Sign On.


## Product and Sprint Backlogs
Product Backlog: Located under 'Projects'


[Scrum Board](https://github.com/users/tron561/projects/1)

## Team Roles
- Product Owner: Alex Hedges
- Integration Specialist: Francesca Suba
- UI Specialist: Vyviene Spaulding

## Communication Time
Tuesdays at 8 PM over Microsoft Teams
