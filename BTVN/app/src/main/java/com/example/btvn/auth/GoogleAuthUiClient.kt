package com.example.btvn.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val clientId: String
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signIn(): BeginSignInResult? {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(clientId) // ✅ không hardcode nữa
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        return try {
            oneTapClient.beginSignIn(signInRequest).await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signInWithIntent(intent: Intent): FirebaseUser? {
        return try {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val idToken = credential.googleIdToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()
            authResult.user
        } catch (e: Exception) {
            null
        }
    }

    fun getSignedInUser(): FirebaseUser? = auth.currentUser

    fun signOut() {
        auth.signOut()
    }
}
