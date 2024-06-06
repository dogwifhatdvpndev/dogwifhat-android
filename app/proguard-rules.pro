# Retrofit (https://github.com/square/retrofit/issues/3751#issuecomment-1192043644)
# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Gson Serialization
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Application
-keep class co.sentinel.vpn.based.network.model.** { *; }
-keep class co.sentinel.vpn.based.common.flags.** { *; }
-keep class com.v2ray.ang.dto.** { *; }

-dontwarn com.google.android.gms.common.GoogleApiAvailability
-dontwarn com.google.android.gms.location.ActivityRecognition
-dontwarn com.google.android.gms.location.ActivityRecognitionClient
-dontwarn com.google.android.gms.location.ActivityRecognitionResult
-dontwarn com.google.android.gms.location.ActivityTransition$Builder
-dontwarn com.google.android.gms.location.ActivityTransition
-dontwarn com.google.android.gms.location.ActivityTransitionEvent
-dontwarn com.google.android.gms.location.ActivityTransitionRequest
-dontwarn com.google.android.gms.location.ActivityTransitionResult
-dontwarn com.google.android.gms.location.DetectedActivity
-dontwarn com.google.android.gms.location.FusedLocationProviderClient
-dontwarn com.google.android.gms.location.LocationCallback
-dontwarn com.google.android.gms.location.LocationRequest
-dontwarn com.google.android.gms.location.LocationResult
-dontwarn com.google.android.gms.location.LocationServices
-dontwarn com.google.android.gms.tasks.OnCanceledListener
-dontwarn com.google.android.gms.tasks.OnCompleteListener
-dontwarn com.google.android.gms.tasks.OnFailureListener
-dontwarn com.google.android.gms.tasks.OnSuccessListener
-dontwarn com.google.android.gms.tasks.RuntimeExecutionException
-dontwarn com.google.android.gms.tasks.Task
-dontwarn com.tobrun.datacompat.annotation.Default

