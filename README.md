# AAQsAwesomeMovieApp

In order to use this app to fetch popular movies, you will use the API from themoviedb.org.

If you don’t already have an account, you will need to create one here: https://www.google.com/url?q=https://www.themoviedb.org/account/signup&sa=D&ust=1525025262335000 in order to request an API Key. In your request for a key, state that your usage will be for educational/non-commercial use. You will also need to provide some personal information to complete the request. Once you submit your request, you should receive your key via email shortly after.

Once you obtain your key, insert it into a java class named 'SecretApiConstants,java' within the Utility dir for the app. add the following code:

public class SecretApiConstants {

public static String movieApiConstant = "{API KEY}";
}

Notes on code usages
-- AsyncTask for checkingConnection -- /* from stackoverflow linked by guide answered Dec 5 '14 at 9:20 Levit https://www.google.com/url?q=http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts&sa=D&ust=1525025262338000 */

Custom view
-- modified sample code from here: https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

Retrofit license:
Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.