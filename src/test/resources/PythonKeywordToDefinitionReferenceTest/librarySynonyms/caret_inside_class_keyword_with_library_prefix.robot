*** Settings ***
Library    module_a.SampleClass     WITH NAME     SomeLibrary

*** Test Cases ***
Some Case
    SomeLibrary.Action<caret>_One
