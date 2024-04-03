// Give the service worker access to Firebase Messaging.
// Note that you can only use Firebase Messaging here. Other Firebase libraries
// are not available in the service worker.
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object

const firebaseApp = firebase.initializeApp({
  apiKey: 'AIzaSyBE1OaWA2Bo3bxh-8oUfJCKGGFz6DkNYbA',
  authDomain: 'funding-gift.firebaseapp.com',
//   databaseURL: 'https://project-id.firebaseio.com',
  projectId: 'funding-gift',
  storageBucket: 'funding-gift.appspot.com',
  messagingSenderId: '184194517827',
  appId: '1:184194517827:web:f2a715c4f6c082503afdf6',
  measurementId: 'G-GPCQJX1FSL',
});

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
    console.log(
      "background message",
      payload
    );
    
    // Customize notification here
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
    };
    
    self.registration.showNotification(notificationTitle, notificationOptions);
  });