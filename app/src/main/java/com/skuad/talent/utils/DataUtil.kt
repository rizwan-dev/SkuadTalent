package com.skuad.talent.utils

import com.skuad.talent.data.model.Candidate

object DataUtil {
    fun getAndroidCandidates() = listOf(
        Candidate(
            candidateName = "Shailee Sharma",
            experience = "Android Developer | 3 years",
            skills = "Android,Kotlin,Java basics,Manual Testing"
        ),
        Candidate(
            candidateName = "Shlok Parashar",
            experience = "Android Trainee | 2 years",
            skills = "C, Java basics"
        ),
        Candidate(
            candidateName = "Shubhi Jyoti Parashar",
            experience = "Sr.Android Developer | 7.4 years",
            skills = "C, Java basics"
        ),
        Candidate(
            candidateName = "Shivi Ankit",
            experience = "Sr.Android Developer | 7 years",
            skills = "Android,Kotlin,Flutter,Java"
        ),
        Candidate(
            candidateName = "Shrihan Sharma",
            experience = "Sr.Android Developer | 6.5 years",
            skills = "Android,Java,Kotlin"
        ),
        Candidate(
            candidateName = "Shrinika Gargav",
            experience = "Sr.Android Developer | 5 years",
            skills = "C++,Java basics,Android"
        )
    )

    fun getIosCandidates() = listOf(
        Candidate(
            candidateName = "Shrikant Borkar",
            experience = "7.6 years",
            skills = "iOS SDK,Objective C,Swift,JAVA basics"
        ),
        Candidate(
            candidateName = "Sampda Dey",
            experience = "5 years",
            skills = "C#, Java basics,XCode "
        ),
        Candidate(
            candidateName = "Kartiki Kamble",
            experience = "6.4 years",
            skills = "C/C++,Objective C,Swift,XCode,Java basics"
        ),
        Candidate(
            candidateName = "Shivi Ankit",
            experience = "7 years",
            skills = "Objective C,XCode,Swift"
        ),
        Candidate(
            candidateName = "Priya Purohit",
            experience = "5.5 years",
            skills = "Xcode ,Objective C, JAVA basics ,Testing"
        )
    )

    fun getBackendCandidates() = listOf(
        Candidate(
            candidateName = "Milan Tiple",
            experience = "9 years",
            skills = "PHP,CSS,Pythn,HTML,SQL,JAVA Script"
        ),
        Candidate(
            candidateName = "Rajit Sharma",
            experience = "3.8 years",
            skills = "PHP,CSS,SQL"
        ),
        Candidate(
            candidateName = "Prafull Waghmare",
            experience = "10.9 years",
            skills = "CSS,JAVA Script,PYTHON,PHP,HTML,Git"
        ),
        Candidate(
            candidateName = "Shaheen Sheikh",
            experience = "7 years",
            skills = "CSS,HTML,PHP,Wordpress,SQL"
        ),
        Candidate(
            candidateName = "Sarita Kandpal",
            experience = "5.3 years",
            skills = "PHP,CSS,SQL,HTML"
        ),
        Candidate(
            candidateName = "Akash Badkul",
            experience = "2 years",
            skills = "PHP,SQL,CSS"
        )
    )
}