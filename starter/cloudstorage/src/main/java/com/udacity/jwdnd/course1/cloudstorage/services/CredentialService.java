package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }
    private Credential encrypt(Credential credential) {
        credential.setKey(encryptionService.generateKey());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(),credential.getKey()));
        return credential;
    }
    public List<Credential> getAllCredentials(int userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public void addCredential(Credential credential) {
        if(credentialMapper.getCredentialByCredentialId(credential.getCredentialId()) == null){
            credentialMapper.addCredential(encrypt(credential));
        } else{
            updateCredential(credential);
        }
    }

    public void updateCredential(Credential credential) {
        credentialMapper.editCredential(encrypt(credential));
    }

    public void deleteCredential(int credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }
}
