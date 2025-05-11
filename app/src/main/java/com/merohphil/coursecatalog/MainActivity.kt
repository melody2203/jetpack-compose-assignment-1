package com.merohphil.coursecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.merohphil.coursecatalog.ui.theme.CourseCatalogTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseCatalogTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val sampleCourses = listOf(
    Course(
        "Introduction to Programming", "SECT-1041", 3,
        "Learn the fundamentals of programming using Kotlin.",
        "None"
    ),
    Course(
        "Data Structures", "SECT-2082", 4,
        "Explore data structures such as lists, trees, and graphs.",
        "Java"
    ),
    Course(
        "Operating Systems", "SECT-3082", 5,
        "Understand OS concepts like processes, memory, and scheduling.",
        "Computer Organization and Architecture"
    ) ,
    Course(
        "Computer Graphics", "SECT-3132", 3,
        "Learn the fundamentals of computer graphics, including 2D and 3D transformations, rendering, shading, and animation.",
        "C++"
    ),
    Course(
        "Mobile App Development", "SECT-3113", 3,
        "Learn to design and build mobile applications using modern frameworks like Kotlin and Flutter.",
        "JavaScript"
    ),
    Course(
        "Artificial Intelligence", "SECT-3151", 2,
        "Covering the basic concepts of AI, including problem-solving,machine learning, and real-world applications.",
        "Python"
    ),
    Course(
        "Cybersecurity", "SECT-3141", 2,
        "Learn how to protect systems and data from cyber threats through practical, hands-on exercises.",
        "Fundamental Of Networking"
    )


)

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            CourseCatalog(courses = sampleCourses)
        }
    }
}

@Composable
fun CourseCatalog(courses: List<Course>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        item {
            Text(
                text = "Course Catalog",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }

        items(items = courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
fun CourseCard(course: Course, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val cardBackgroundColor = if (isSystemInDarkTheme()) Color(0xFF1E1E2F) else Color(0xFFCCE5FF)
    val buttonColor = Color(0xFF7A5FFF)

    Card(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = "Title: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = course.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                Text(
                    text = "Code: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = course.code,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row {
                Text(
                    text = "Credit Hours: ",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = course.creditHours.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Description: ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = course.description,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        text = "Prerequisites: ",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = course.prerequisites,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(extraPadding.coerceAtLeast(0.dp)))

            ElevatedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A5ACD),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (expanded) "Show less" else "Show more",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Course Catalog App!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    CourseCatalogTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    CourseCatalogTheme {
        MyApp(Modifier.fillMaxSize())
    }
}
@Preview(showBackground = true, name = "Light Mode")
@Composable
fun CourseCardPreviewLight() {
    CourseCatalogTheme(darkTheme = false) {
        CourseCard(
            Course(
                "Intro to Programming", "CS101", 3,
                "Learn basic Kotlin programming.",
                "None"
            )
        )
    }
}
@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CourseCardPreviewDark() {
    CourseCatalogTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CourseCard(
                Course(
                    "Intro to Programming", "CS101", 3,
                    "Learn basic Kotlin programming.",
                    "None"
                )
            )
        }
    }
}