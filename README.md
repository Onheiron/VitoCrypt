VitoCrypt
=========

Multilevel media encryption for Android


Goals
======

This is an **exercise application** aiming to provide *three levels* of "security" of wich *only the last one is actual strong encryption*.

Here is the list of the three levels with relative goals:

 - **Protection Level** : This level only slightly protects your datas. It does so by XORing part (or all) of you file bits with (an hash of) your SIM's IMSI value. This is intended to be a fast encryption to prevent common users to see your files when : 
      - You lend them your phone to use with their SIM (f.e. your doughter phone breaks, you lend her yours and put your SIM in that old Motorola you got).
      - Common user gets hold of your SD card but not your phone (f.e. you leave your SD card on the office table / you lend your SD card to a friend so he can listen to some music on it [but not see your protected pictures])

 - **Obscuration Level** : This level is just like level one, but requires a password for correct decryption (the password hash will be chained to the SIM's IMSI hash in the XORing process). This is intended to be a fast password encryption to prevent common users to see your files when :
      - You lend them your phone with your SIM inside of it (f.e. your doughter doesn't have a phone, you lend her yours with the SIM inside so she can call home when she's at the bus stop)
      - Common user gets hold of your phone (f.e. you leave your phone on the office desk / someone steals or finds your phone)


All the levels above provide fast low level encryption and require backup (full file decryption) in case of legit SIM switch (this is also why they're fast). **Protection Level** is password-less for fast file access (you don't want to type a password every time you want to check those files).
 
 - **Encryption Level** : This level is real encryption. It uses AES encryption with double password (or passphrase) to encrypt your files. Encryption won't depend on SIM card and it is intended to withstand more dedicated attacks. This level of encryption is rather slow and direclty depends on the passwords' (passphrase's) length.

Components
======

The app is composed of:

 - `ProtectedFragment` : `Fragment` used to list files at *Protection Level*. It decrypts files inside the `/PRT/` folder to the `/TMP/` (temporary) folder.
 - `ObscuredFragment` : `Fragment` used to list files at *Obscuration Level*. It decrypts files inside the `/SHD/` folder to the `/TMP/` (temporary) folder.
 - `CryptedFragment` : `Fragment` used to list files at *Encryption Level* . It decrypts files indise the `/CYP/` folder to the `/TMP/` (temporary folder).
 - `AddFiles` : `Activity` used to add / remove files to / from encryption folders. 
 - `Protector` : executes file *Protect* / *UnProtec* by writing / reading in the `/PRT/` directory.
 - `Shader` : executes file *Shade* / *UnShade* by writing / reading in the `/SHD/` directory.
 - `Encrypter` : executes file *Encrypt* / *Decrypt* by writing / reading in the `/CYP/` directory.
 - `ImageViewer` : renders temporary decrypted images with pinch zoom capabilities.
 - `VideoViewer` : plays the temporary decrypted videos.
 - `AudioPlayer` : plays the temporary decrypted audio.

Issues and wannabe
======

Current app status is **unfinished**, it is intended as a *spare time exercise* and **does not guarantee** (at this point) **file integrity and password protection** (i.e. don't use this for any file you don't want to eventually loose).

Current issues are:

 - How to delete the temporary files as often as possible so that they won't remain in clear inside the `/TMP/` folder?
 - How to play video and audio inside the relative activities?
 - How to protect / obscure audio tracks?
 - How to make everything work really fast or at least keep good track of process progresses.

This app may aim to become:

 - A really fast multimedia protection system to keep your media files safe from common user display.
 - An handy yet secure file browser for crypted files.
 - A strong cryption system for more sensible files.
 - A file corruption / modification detector for encrypted files.
