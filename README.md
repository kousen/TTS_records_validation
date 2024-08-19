Demonstration project to show how to validate the properties of Java records, using the OpenAI text-to-speech service as an example.

You can run the `TTSRequestTest` without any additional dependencies. To run the `TextToSpeechServiceTest` (in other words, to actually use the service to generate audio from text),
you need to register at https://platform.openai.com, generate a key, and save it as an environment variable called `OPENAI_API_KEY`.

The process involves:

1. Define relevant constants
2. Create enums
3. Add overloaded constructors
4. Add a compact constructor
5. Apply bean validation annotations

Good luck,

Ken Kousen
