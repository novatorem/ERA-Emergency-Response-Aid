<p align="center">
  <b>ERA: Emergency Response Aid</b>
</p>
<p align="center">
Team application designed as a mediatery to help users self diagnose themselves or seek medical attention
</p>


| Member  | Role |
| ------------- | ------------- 
| Abdulla Bin Haji  | Front End  |
| Dellis Ng | Real-time Engineer |
| Aun Sheikh  | System Architecture  |
| Spasimir | API Integration |
| Teddy | Tracking and Locality |



<p align="center">
  <img src="http://i.imgur.com/Qx1Qnu5.png">
</p>


#### Tools, Techniques, Conventions, that worked

Android studio was great to work with in terms of ease of use, allowing lots of autocomplete and suggested features to be introduced seamlessly. Due to our lack of design experience, we used copyright free icons or crafted our own using simple software such as Paint.NET. The method of development we chose (agile) was more due to the way the professor wanted to track our app development (through the use of pivotal tracker, sprints, scrum meetings), but it worked well since we're all familiar with it and the steps are rather simple. Github was also nice to use for our version control, especially due to the nature of Android development, we had lots of branches to allow everyone to work their code without breaking another's. Something else that worked really well was the medical API. After all the trouble of rewriting the code to work with the newest version of android the API was returning correct information very quickly.

#### Tools, Techniques, Conventions, that failed

Initially, we started our application using activities/views, which was fine until we decided to implement an application bar, menu, and navigation drawer. The three required the use of fragments, which is by allowing everyone to be integrated into another instead of starting new functions. It failed though, due to us having already written all our code into activities. The process of attempting to change to fragments in turn broke existing code (specifically the self-diagnosis feature), which meant we had to instead write a hardcoded hack that simply set the application bar in stone instead of allowing it to flow with the application. Something else that could have been better is communication. For the most part it was going well because we were using tools that everyone has access to (Facebook Messenger and Discord) but there were some times when just couldn't get a hold of some members for a long time.

#### Design & Process for the future

We do plan on continuing development (at least those still interested), but not as a priority. As such, our members will simply take some time every once in a while, and introduce new features or brush up on old ones when time permits. We won't be following any development methods, instead everyone choosing their own personal style. Eventually, when it was brushed up enough and polished, we hope to push it to the Google Play Store, perhaps add a monetization feature to allow for scalability of the application. We plan on adding a clickable body image where a user can click on certain body parts of an image instead of using just radio buttons. We also plan on making the "diagnosis" and "more info" pages more pleasant to look at. So far the app only works with identifying internal diseases. We also plan on making it work for external things too, such as treatments for a broken finger or a deep cut. Another feature that we would like to add is step-by-step guides with pictures of how to do each treatment. Last but not least we want to add support for wearable devices so that we can get other vital information about our users.

