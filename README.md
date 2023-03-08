# ProgressBar 🍫
Simple and customizable Progress bar for Android 

<p align="center">
<img src="https://user-images.githubusercontent.com/54546499/222962979-1ac852a3-b140-4622-a0cc-a7460ada67b6.gif" width="400" height="800"/>
<p/>

## How to include in you project:
``` grovy
implementation "io.github.rool78:progressbar:0.0.1"
```

## Create ProgressBar via xml:
You can insert the progress bar in you xml as usual, you can set those attibutes if desired:
```xml
        <!-- progress-->
        <attr name="progressBar_progress" format="integer" />
        <!--  color of the progressbar. -->
        <attr name="progressBar_colorProgress" format="color" />
        <!--  color of the container. -->
        <attr name="progressBar_colorBackground" format="color" />
        <!--  Default text of the progressbar label. -->
        <attr name="progressBar_labelText" format="string" />
        <!--  color of the progressbar. -->
        <attr name="progressBar_colorLabelText" format="color" />
        <!--   animate progress bar-->
        <attr name="progressBar_isAnimated" format="boolean" />
        <!--   duration of animation-->
        <attr name="progressBar_animationDuration" format="integer" />
        <!-- animation type-->
        <attr name="progressBar_animationType" format="enum">
            <enum name="linear" value="0" />
            <enum name="anticipate" value="1" />
            <enum name="accelerate" value="2" />
            <enum name="bounce" value="3" />
            <enum name="accelerateDecelerate" value="4" />
        </attr>
```
Example:
```xml
    <com.rool.progressbar.ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="match_parent"
        android:layout_height="20dp"
        android:layout_marginTop="20dp"
        android:layout_marginHorizontal="28dp"
        app:progressBar_colorProgress="@color/black"
        app:progressBar_colorLabelText="@color/purple_700"
        app:progressBar_isAnimated="true"
        app:progressBar_animationDuration="2000"
        app:progressBar_animationType="accelerate"
        />

```
You can also set the values programatically

## Coming soon:
Progress bar implemented in jetpack compose

## License
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
