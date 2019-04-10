*** Settings ***
Library       OperatingSystem
Library       library
Library       library.MyLibrary

*** Variables ***
${MESSAGE}    Hello, world!

*** Test Cases ***
My Test
    [Documentation]    Example test
    [Tags]    TAG1    TAG2
    [Setup][Teardown]
        Specify test setup and teardown. Have also synonyms [Precondition] and [Postcondition], respectively.
    [Template]
        Specifies the template keyword to use. The test itself will contain only data to use as arguments to that keyword.
    [Timeout]
    Log    ${MESSAGE}
    My Keyword    /tmp

Second Test
    Given something
    When something else
    Then we are happy

Another Test
    Should Be Equal    ${MESSAGE}    Hello, world!

One More Test
    Hello    world
    Do Nothing
    Keyword From Class

*** Keywords ***
My Keyword
    [Arguments]    ${path}
    Directory Should Exist    ${path}

*** Tasks ***
Process invoice
    Read information from PDF
    Validate information

