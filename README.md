# Email Bomber

## Overview

**Email Bomber** is a Spring Boot application designed to send bulk AI-generated emails using Gmail and Google Gemini. The application demonstrates how to automate the creation and distribution of unique, human-like email bodies leveraging Google's generative AI API.

- **BulkEmailRunner** orchestrates the process of generating and sending emails.
- **GeminiService** interacts with the Gemini generative language API to create unique email bodies for each message.
- **GmailService** handles Gmail API authentication and sending emails.

## Features

- **Bulk Email Sending**: Automatically sends a series of emails to a specified recipient.
- **AI-Generated Content**: Email bodies are generated using Google Gemini, making each message unique and engaging.
- **Gmail Integration**: Uses Google's Gmail API for reliable delivery.

## How It Works

1. **BulkEmailRunner** runs on application startup and loops through a predefined number of emails.
2. For each email:
   - **GeminiService** generates the body text via Google's Gemini API.
   - **GmailService** sends the email to the recipient using Gmail credentials.
   - Progress and errors are logged to the console.

## Key Classes

| Class                    | Responsibility                                                      |
|--------------------------|---------------------------------------------------------------------|
| `BulkEmailRunner`        | Main runner that coordinates email generation and sending           |
| `GeminiService`          | Calls Gemini API to generate friendly, unique email content         |
| `GmailService`           | Authenticates and sends emails via Gmail API                        |

## Setup Instructions

1. **Clone the repository**:
   ```sh
   git clone https://github.com/eliteclown/email_bomber.git
   ```
2. **Configure Credentials**:  
   Update your `application.properties` or environment variables with:
   - `gmail.clientId`
   - `gmail.clientSecret`
   - `gmail.refreshToken`
   - `gmail.senderEmail`
   - `gemini.apiKey`

3. **Build and Run**:
   ```sh
   ./mvnw spring-boot:run
   ```
   The application will send emails automatically on startup.

## Requirements

- Java 17+
- Maven
- Google Cloud account with Gmail and Gemini API access

## Customization

- **Recipient Address**:  
  Change the recipient email in `BulkEmailRunner` (`recipient` variable).
- **Number of Emails**:  
  Modify the loop in `BulkEmailRunner` to adjust the number sent.

## Disclaimer

This project is for educational purposes. Sending unsolicited bulk email may be considered spam and violate terms of service or local laws.

---
