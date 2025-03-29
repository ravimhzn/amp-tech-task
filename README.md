# AMP TECH TASK 
The Challenge as per PDF provide:
We’d like you to develop a “round-up” feature for AMP Bank customers using Engine’s public developer API.
For a customer, take all the transactions in a given week and round them up to the nearest dollar. For example
with spending of $4.35, $5.20 and $0.87, the round-up would be $1.58. This amount should then be transferred
into a savings goal, helping the customer save for future adventures.
Note: You should use the default currency of the account for creation of the savings goal. For more details - [Click here.](https://github.com/ravimhzn/amp-tech-task/blob/main/question/amp_tech_task.pdf)

# IMPORTANT NOTES:
- Please note that by the time of testing the app, the access token may have expired, as it is only valid for 24 hours. The access token will need to be manually updated using the Starling Developer Account. I'll try to update it daily, but if I haven't, please feel free to ping me. Alternatively, if you have a developer account, you can update the access token under AccountRepository.
```aidl
private val accessToken = "NEW_ACCESS_TOKEN" //Valid only 24 hours.
```
- The amount returned by the transaction API is in pounds (£) instead of dollars ($). Therefore, I'll refer to it as the default currency for this task to avoids currency conversion issues.
- There is limited clarity on the Saving Goals API, which is why I've only simulated how the rounded-up amount would be transferred to Saving Goals (please refer to the screenshots below). Let me know if any further adjustments are needed.
- Regarding the weekly transaction, I've taken it a step further by adding a calendar feature that allows the user to choose a date range, whether weekly or any custom range.

# Screenshots
<table style="width:100%">
  <tr>
    <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/homepage_error.png" width="250"></td>
    <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/homepage.png" width="250"></td>
    <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/select_dates.png" width="250"></td>
    <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/dates_selected.png" width="250"></td>
    <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/transfer_success.png" width="250"></td>
  </tr>
</table>
<table style="width:100%">
 <td><img src="https://github.com/ravimhzn/amp-tech-task/blob/main/images/amp_tech.gif" width="250"></td>
</table>


# About
This sample application, part of the Amp-Tech-Task, is built using declarative programming and follows Clean MVVM architecture. It leverages the latest tech stacks and best practices to ensure scalability, maintainability, and a state-driven UI that updates automatically based on changes in the underlying state.

Tech Stack:
- Kotlin – Primary programming language
- Coroutines
- State Management – Efficient UI updates
- Hilt For Dependency injection
- Retrofit For Networking
- Material3 For UI components
- JUnit & Compose UI Test – Unit and UI testing

# Using commandLine:
```aidl
./gradlew clean build
./gradlew connectedDebugAndroidTest
```
