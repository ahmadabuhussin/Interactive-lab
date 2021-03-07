package com.nk.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.*
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark.*
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions

import com.otaliastudios.cameraview.Frame
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val common = Properties()
    private val personList = ArrayList<Person>()//Creating an empty arraylist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        cameraView.setLifecycleOwner(this)
        cameraView.addFrameProcessor {
            extractDataFromFrame(it) { result ->
//                tvDetectedObject.text = result
            }
        }
    }

    private fun extractDataFromFrame(frame: Frame, callback: (String) -> Unit) {

        val options = FirebaseVisionObjectDetectorOptions.Builder()
                .setDetectorMode(FirebaseVisionObjectDetectorOptions.STREAM_MODE)
                .enableClassification()  // Optional
                .build()
        val objectDetector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options)

        if (frame.data != null) {
            objectDetector.processImage(getVisionImageFromFrame(frame))
                    .addOnSuccessListener {
                        var result = ""

                        it.forEach { item ->
                            result += "${item.trackingId}\n"  //TODO : Get the knowledge graph result for this entity
                            val bounds = item.boundingBox  // The object's position in the image
                            if (bounds.height() > 500) {

                                val findedPerson = personList?.find {
                                    it.common.trackingId == item.trackingId.toString();
                                }
                                if (findedPerson != null) {
                                    //your code here
                                    findedPerson.common.lastPosition = bounds.left
                                    findedPerson.common.timer()
                                    findedPerson.common.height = bounds.height()
                                } else {
                                    val person = Person()
                                    person.common.trackingId = item.trackingId.toString()
                                    person.common.firstPostion = bounds.left;
                                    person.common.lastPosition = bounds.left
                                    person.common.height = bounds.height()
                                    person.common.context = this
                                    person.common.timer()
                                    personList.add(person)
                                }

                                Log.e(
                                        "hello",
                                        item.trackingId.toString() + " " + bounds.left.toString()
                                )

                            }
                        }
                        callback(result)
                    }
                    .addOnFailureListener {
                        callback("Unable to detect an object")
                    }
                    .addOnCompleteListener {
                        it.getResult()?.forEach { item ->
                            val bounds = item.boundingBox  // The object's position in the image
                            if (bounds.height() > 100) {
                                Log.e("hello", item.trackingId.toString() + "complete")
                            }
                        }
                        it.result
//            Log.e("hello", it.result.toString()+"complete")

                    }
        }
    }

    private fun getVisionImageFromFrame(frame: Frame): FirebaseVisionImage {
        //ByteArray for the captured frame
        val data = frame.data

        //Metadata that gives more information on the image that is to be converted to FirebaseVisionImage
        val imageMetaData = FirebaseVisionImageMetadata.Builder()
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                .setRotation(FirebaseVisionImageMetadata.ROTATION_90)
                .setHeight(frame.size.height)
                .setWidth(frame.size.width)
                .build()

        val image = FirebaseVisionImage.fromByteArray(data, imageMetaData)

        return image
    }

}
