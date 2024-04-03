import { initializeApp } from "firebase/app";
import { getMessaging, onBackgroundMessage } from "firebase/messaging/sw";

// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object
const firebaseApp = initializeApp({
    apiKey: 'AIzaSyBE1OaWA2Bo3bxh-8oUfJCKGGFz6DkNYbA',
    authDomain: 'funding-gift.firebaseapp.com',
    // databaseURL: 'https://project-id.firebaseio.com',
    projectId: 'funding-gift',
    storageBucket: 'funding-gift.appspot.com',
    messagingSenderId: '184194517827',
    appId: '1:184194517827:web:f2a715c4f6c082503afdf6',
    measurementId: 'G-GPCQJX1FSL',
  });

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = getMessaging(firebaseApp);

onBackgroundMessage(messaging, (payload) => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png'
  };

  self.registration.showNotification(notificationTitle,
    notificationOptions);
});