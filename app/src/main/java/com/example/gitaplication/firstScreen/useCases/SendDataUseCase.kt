package com.example.gitaplication.firstScreen.useCases

import com.example.gitaplication.repositories.Repository
import com.multiplatform.util.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendDataUseCase(
    private val repository: Repository
) : UseCase {
    suspend operator fun invoke(mac: String, contacts: Array<String>) = withContext(Dispatchers.IO) {
        repository.submitData(mac, contacts)
    }
}