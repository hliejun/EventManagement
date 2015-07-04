# EventManagement
Orbital 2015: 404: NAME_NOT_FOUND


Maintaining a healthy social life is important and the best way to do so is to plan for outings, events and getaways from our work life! Our project aims to encourage social activities among our users by simplifying the way we manage a social event. Our application will assist users in organizing social groups and settling costs, debts and logistical needs between users.


Our base application will have 3 main functionalities: An invitation and social group management system that allows users to manage their social groups, contacts and send event invitations within a single touch; a recommendation system that keeps a database of recommended, popular events and activities which users may consider for their outing; a cost handling system that documents transactions made during the outing or event and calculates the cost due for each individual in the group.


On top of the base functionalities, we would like to add Facebook and Android Contacts integration for our invitation and group management system, a debt notification system that extends from the cost handling system, online database for events and activities, and should time allows, OCR (Optical Character Recognition) for automated documentation of receipts, and a trophy / achievement / gamification system that makes the entire user experience more exciting.


Currently, as of 29 June 2015, we have completed several activity frames in Android Studio, and have grasped the fundamentals of our UI design concept. We have dissected the application into multiple branches for modular programming and will work diligently towards expanding the functionality and usability of our application, such as introducing Facebook integration, event recommendation, and a more pleasing user interface.


We have decided on different functionality of our application by constantly taking on the role of the user and asking ourselves the different aspect of the application that we would like to see and achieve in the end­product. These are the sets of user stories that we came up with to determine the basic functionalities of our application:

  ● As a user, I wish to send invitations with event details with the least amount of actions so that the entire planning of events feels smooth and easy instead of a chore.
  
  ● As a user, I wish to settle the group bills in a quick and accurate manner, so that everyone involved can be informed of their payment due and pay the right amount.
  
  ● As a user, I wish to know what recommended places to go in a tap during the event planning process so that it would not kill the excitement of planning for an outing.
  
  ● As a user, I wish to keep an updated record of the amount of money I owe or what others owe me, so that debts can be settled accurately and punctually.


As our knowledge and experience with the Android SDK grows, we get a better grasp on what is practical and most importantly within our time­frame and ability to program. Utilising these new­found experience, we once again take on the role of the user and came up with the following new set of user stories that we hope to accomplish by the end of Orbital 2015:

  ● As a user, I wish to send out automated reminders to the people that owes me money in a comedic, creative and light­hearted manner by using possibly funny messages to soften the tone and mood, so that my debtor will return the money on time yet preserving our relationship.
  
  ● As a user, I wish to decide on different, creative methods to handle my debts, for example choosing to waive it, convert it into a meal or a treat so that more opportunities of hanging out with my friends are created.
  
  ● As a user, I wish to keep track of the events I have hosted, so that I can host new events with minimal repeats.


In general, we have split the functions of our application into 2 aspects ­ Events and Debts. For the case of Events, the UI has yet to be implemented, hence it will not be available for testing. For the backend code, we would most likely be relying heavily on pre­written APIs online due to time constraints. There are 3 main features in Events:

  ● Creation of Events
  
  ● Creation of Groups
  
  ● Archive of Events


All of which will be implemented in July, as we concurrently source for the appropriate APIs to use.


The second aspect of our application is the Debt/Bill (D&B) functions. We have spent most of our time in June coding for the backend portion of this function, mainly our own class and APIs. Primarily, there are 7 activities (pages) for D&B functions and we have successfully implemented 5 out these 7 activities, putting us on track for the UI progress.


In terms of user stories, we managed to allow our users to create bill(s) where each bill is a list of purchases, and these bills are successfully recorded and archived as debts. Hence 2 of the initial user stories have been addressed.


However, there have been some setbacks in the backend portion of the UI. We have been able to overcome most of our setbacks, leaving our most urgent problem as the passing of data/objects from an activity to another activity/fragment. Hence as of current stage, the D&B function is not yet ready for User Acceptance testing until we resolve the above stated issue, but we foresee swift resolution in the upcoming days.
