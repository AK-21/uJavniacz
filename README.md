# uJavniacz
Very simple library provides javax.swing log output window and dialog windows, to helps application event monitoring.

<strong>What is uJavniacz?</strong>

uJavniacz is a java library, which was created to help inspect program running.
Program events may be printed in external javax.swing output window, displayed in dialog windows and saved to txt files.
Library functionalities may be helpful in development process, and may be also used to inform end user about application errors.

<strong>How to use uJavniacz?</strong>

The most simple way is use one of report functions:

<code>

ujavniacz.Tool.get().rptInfo(String header, String description)

ujavniacz.Tool.get().rptWarning(Exception e)

ujavniacz.Tool.get().rptWarning(String header, String description)

ujavniacz.Tool.get().Tool.get().rptCritical(Exception e)

ujavniacz.Tool.get().Tool.get().(String header, String description)

</code>


Tool will be initialized automatically, when first use.




Output window may be initialized by function:
<code>
  Tool.get().enableOutputWindow();  
</code>

Data collecting in log may be enabled by function:
<code>
  Tool.get().enableLog()  
</code>
</br></br>

<strong>For more information see project documentation</strong>
