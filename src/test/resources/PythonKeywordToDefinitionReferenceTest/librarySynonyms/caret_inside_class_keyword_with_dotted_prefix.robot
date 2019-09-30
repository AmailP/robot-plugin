*** Settings ***
Library    module_a.SampleClass     WITH NAME     Some.Library

*** Test Cases ***
Some Case
    Some.Library.Action<caret>_One
