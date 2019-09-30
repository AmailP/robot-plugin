*** Settings ***
Library       module_a
Library       module_a.SampleClass     WITH NAME     SomeLibrary

*** Test Cases ***
Some Case
    SomeLibrary.multiple_def<caret>ined
