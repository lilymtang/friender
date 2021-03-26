package com.example.twitchandroidproject.repository

import com.example.twitchandroidproject.repository.model.UserAccount
import com.example.twitchandroidproject.repository.model.UserProfile
import java.util.Calendar


/**
 * Object containing helper functions / constants for test data creation
 */
object TestDataUtil {

    const val ACCOUNT_PASSWORD_CORRECT = "correctPassword"
    const val ACCOUNT_PASSWORD_INCORRECT = "incorrectPassword"

    fun createInitialUserProfiles() = listOf(

        createCurrentUserProfile(),

        UserProfile(
            email = "moni@gmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Moni",
            dateOfBirth = dateOfBirthForAge(21),
            bio = "I graduated from Berkeley during the pandemic and my 21st birthday trip to Las Vegas was cancelled. The pandemic has changed my life in a lot of ways, but some positives have come out of it: I started doing yoga, I started journaling every day, and I started dedicating a lot more time to digital art.",
            profilePicture = "https://i.pinimg.com/originals/72/39/37/7239378f2717fcc75369f5035df1f048.jpg",
            interests = listOf("Drawing", "Baking", "Yoga"),
            preferredInterests = listOf(
                "Reading",
                "Running",
                "Gym"
            ),
            latitude = 37.5622058,
            longitude = -122.1840744,
            phoneNumber = "510-365-3456"
        ),
        UserProfile(
            email = "marie@gmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = false,
            fullName = "Marie",
            dateOfBirth = dateOfBirthForAge(26),
            bio = "I've been doing ballet since I was in elementary school and I enjoy all forms of dance in general. I've been trying to get more involved in the dance community in the city.",
            profilePicture = "https://images.unsplash.com/photo-1483381616603-8dde934da56f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1355&q=80",
            interests = listOf("Dance", "Running", "Painting", "Gym"),
            preferredInterests = listOf(
                "Dancing",
                "Backpacking"
            ),
            latitude = 37.725,
            longitude = -122.277,
            phoneNumber = "949-930-2835"
        ),
        UserProfile(
            email = "mark@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Mark",
            dateOfBirth = dateOfBirthForAge(24),
            bio = "Looking for someone who dislikes brunch as much as I do. $13 for eggs? No thanks.\n\nPros:\n-grilled cheese king.\n-lives alone.\n-better looking in person.\n\nCons:\n-not incredibly creative at bio writing",
            profilePicture = "https://images.unsplash.com/photo-1598096969068-7f52cac10c83?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=2550&q=80",
            interests = listOf("Sewing", "Podcasts", "Rowing"),
            preferredInterests = listOf(
                "Beer",
                "Kayaking",
                "Backpacking"
            ),
            latitude = 37.582579,
            longitude = -122.0412517,
            phoneNumber = "626-127-5641"
        ),
        UserProfile(
            email = "john@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "John",
            dateOfBirth = dateOfBirthForAge(27),
            bio = "Hi I'm John! I grew up in Georgia and I moved to the Bay Area when I was 10. I spend as much time as possible in the outdoors. I make a mad smoked brisket. Looking for friends to share good food and good stories with.",
            profilePicture = "https://images.unsplash.com/photo-1525336868028-a66fb94a2b72?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
            interests = listOf("Running", "Grilling", "Craft Beer", "Fashion", "Hip Hop", "Gym"),
            preferredInterests = listOf(
                "Camping",
                "Woodworking",
                "Bouldering"
            ),
            latitude = 37.5644691,
            longitude = -122.07,
            phoneNumber = "802-365-9358"
        ),
        UserProfile(
            email = "larajean@gmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Lara Jean",
            dateOfBirth = dateOfBirthForAge(18),
            bio = "I am the middle sister. I have an older sister Margot, and a younger sister Kitty. I'm matriculating at NYU this coming Fall semester and I'm so excited to move to a big city! I'm a stress baker; hit me up for cupcakes and cookies when I'm stressed.",
            profilePicture = "https://pyxis.nymag.com/v1/imgs/07c/f68/3f01d68905b4979a1d8a405691455c10f6-lana-condor.rsquare.w700.jpg",
            interests = listOf("Fashion"),
            preferredInterests = listOf(
                "Writing",
                "Baking",
                "Reading"
            ),
            latitude = 37.6531623,
            longitude = -122.221768,
            phoneNumber = "360-360-5243"
        ),
        UserProfile(
            email = "david@gmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "David",
            dateOfBirth = dateOfBirthForAge(32),
            bio = "Sup folks. I'm new to the Bay Area and hoping to find some people to hang with on the weekends. I live in the Mission so catch me at Mission Dolores on the weekends and let's go get some fire burritos!",
            profilePicture = "https://images.unsplash.com/photo-1515756142405-7a5a42b76c6c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
            interests = listOf("Gaming", "Reading", "Hiking", "Gym"),
            preferredInterests = listOf(
                "Surfing",
                "Cocktails",
                "Guitar"
            ),
            latitude = 37.7255046,
            longitude = -122.2772,
            phoneNumber = "273-593-3467"
        ),
        UserProfile(
            email = "jane@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Jane",
            dateOfBirth = dateOfBirthForAge(25),
            bio = "What are your preferences? Beach vs. Hike; Gym vs. Club; Salad vs. Burger.",
            profilePicture = "https://images.unsplash.com/photo-1607529379169-5804750c1f9a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
            interests = listOf("Reading", "Coffee"),
            preferredInterests = listOf(
                "Art",
                "Yoga",
                "Running"
            ),
            latitude = 37.6531623,
            longitude = -122.22,
            phoneNumber = "504-867-8220"
        ),
        UserProfile(
            email = "mike@egmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Mike",
            dateOfBirth = dateOfBirthForAge(29),
            bio = "Hi, I'm Mike. I'm a professional photographer for National Geographic. I've been to 86 countries, and my favorite is Turkey. The food is incredible! I train Muay Thai as often as possible and hit up comedy shows when I can. I believe more in humanity than politics or religion. Because of my travel schedule, I'm never one in place for very long.",
            profilePicture = "https://images.unsplash.com/photo-1613322800554-25691677df22?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=760&q=80",
            interests = listOf("Standup Comedy", "Muay Thai", "Philosophy"),
            preferredInterests = listOf(
                "Rock Climbing",
                "Singing",
                "Piano"
            ),
            latitude = 37.6531623,
            longitude = -122.22,
            phoneNumber = "812-236-6573"
        ),
        UserProfile(
            email = "jim@gmail.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Jim",
            dateOfBirth = dateOfBirthForAge(33),
            bio = "I am a paper salesman at Dunder Mifflin in Scranton, Pennyslvania. I never thought I'd be selling paper for a living. Office life is okay; at least there is my buddy Dwight to spice up the everyday grind.",
            profilePicture = "https://topicimages.mrowl.com/large/netflixislife/theoffice/myfavoritechar/jimhalpert_1.jpg",
            interests = listOf("Sports", "Baseball", "Comic Books", "Sci-Fi"),
            preferredInterests = listOf(
                "Coffee",
                "Basketball",
                "Cycling"
            ),
            latitude = 37.6531623,
            longitude = -122.221768,
            phoneNumber = "407-300-1660"
        ),
        UserProfile(
            email = "christopher@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Christopher",
            dateOfBirth = dateOfBirthForAge(30),
            bio = "Into nonfiction and current events, the gym & anything fitness related, and travel (80+ countries).",
            profilePicture = "https://images.unsplash.com/photo-1500027202745-eec1ad6523cd?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80\n",
            interests = listOf("Guitar", "Gaming", "Hiking"),
            preferredInterests = listOf(
                "Rock Climbing",
                "Singing",
                "Travel"
            ),
            latitude = 37.5622058,
            longitude = -122.1840744,
            phoneNumber = "510-347-5641"
        ),
        UserProfile(
            email = "jen@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Jen",
            dateOfBirth = dateOfBirthForAge(30),
            bio = "I'm an aspiring writer currently working as a manager at a coffee shop. I can do some pretty impressive latte art, but personally, I'm more of a black coffee kinda girl. What's your drink of choice?",
            profilePicture = "https://images.unsplash.com/photo-1599780196508-80547099535c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            interests = listOf("Guitar", "Gaming", "Dancing"),
            preferredInterests = listOf(
                "Coffee",
                "Pilates"
            ),
            latitude = 37.485579,
            longitude = -122.1212517,
            phoneNumber = "917-347-5641"
        ),
        UserProfile(
            email = "emily@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = false,
            fullName = "Emily",
            dateOfBirth = dateOfBirthForAge(22),
            bio = "Favorite Friday night activity: opening a bottle of wine and cooking a new recipe / Favorite Saturday morning activity: taking my daughter to the park / What I'm watching on Netflix right now: Cobra Kai.",
            profilePicture = "https://www.psychmechanics.com/wp-content/uploads/2015/07/smiling-woman.jpg",
            interests = listOf("Dancing", "Cocktails", "Bartending"),
            preferredInterests = listOf(
                "Cooking",
                "Hiking",
                "Bartending"
            ),
            latitude = 37.485579,
            longitude = -122.1212517,
            phoneNumber = "626-347-5641"
        ),
        UserProfile(
            email = "justin@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = false,
            fullName = "Justin",
            dateOfBirth = dateOfBirthForAge(25),
            bio = "3 foods I can't live without: NYC bagels, late-night nachos, my mom's lasagna.",
            profilePicture = "https://images.unsplash.com/photo-1492462543947-040389c4a66c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            interests = listOf("Basketball", "Gaming", "Backpacking"),
            preferredInterests = listOf(
                "EDM",
                "Hip Hop",
                "Soccer"
            ),
            latitude = 37.482579,
            longitude = -122.1412517,
            phoneNumber = "626-347-5641"
        ),
        UserProfile(
            email = "avery@email.com",
            userProfileType = UserProfile.UserProfileType.OTHER,
            isAvailableToHangout = true,
            fullName = "Avery",
            dateOfBirth = dateOfBirthForAge(29),
            bio = "LA raised>>NYC living\nCorporate slave Mon-Fri\nAdventure seeker on the weekend",
            profilePicture = "https://images.unsplash.com/photo-1536838960759-6e236ca6e42e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80",
            interests = listOf("Baseball", "Gaming", "Philosophy"),
            preferredInterests = listOf(
                "Basketball",
                "Hip Hop",
                "Soccer"
            ),
            latitude = 37.582579,
            longitude = -122.0412517,
            phoneNumber = "626-457-5641"
        )
    )

    fun createCurrentUserProfile() =
        UserProfile(
            email = "lily@gmail.com",
            userProfileType = UserProfile.UserProfileType.CURRENT_USER,
            isAvailableToHangout = true,
            fullName = "Lily",
            dateOfBirth = dateOfBirthForAge(24),
            bio = "I grew up in Fremont, California. I started backpacking in 2019 and it has become the main event of my weekend warrior antics. Ask me which backpacking permits I\'m applying for this season!\n\nI also just love being active in general, whether it means going to the gym (pre-COVID), trying to do more frequent runs, or taking long walks to San Francisco bakeries to try all their pastries.",
            profilePicture = "https://images.squarespace-cdn.com/content/v1/58ddd25a15d5db2b31d11726/1614159878477-BOZXEPZ3VRRRRRAJDSNV/ke17ZwdGBToddI8pDm48kEP3XILZbd6clkwwwPPDzGJ7gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QPOohDIaIeljMHgDF5CVlOqpeNLcJ80NK65_fV7S1USJfBMlwhtiUUFKQ2Qtzx-UONIfB-9RFWha3Hf_VomFI5ck0MD3_q0rY3jFJjjoLbQ/Fontanillis-Lake-Dicks-Peak-Desolation-Wilderness-1325.jpg?format=2500w",
            interests = listOf("Piano", "Rock Climbing", "Cooking", "Running", "Blogging"),
            preferredInterests = listOf(
                "Backpacking",
                "Baking",
                "Photography"
            ),
            latitude = 37.44,
            longitude = -122.12,
            phoneNumber = "510-353-3456"
        )

    fun createInitialUserAccount() =
        UserAccount(email = "lily@gmail.com", password = "lily")

    // helper function that creates dateOfBirth of provided age
    // to simplify test user data creation
    private fun dateOfBirthForAge(age: Int) =
        Calendar.getInstance().apply {
            add(Calendar.YEAR, -age)
        }.time
}