import * as React from 'react';

import { StyleSheet, View } from 'react-native';
import { MediaCardCarousel } from 'react-native-media-card';

export default function App() {
  return (
    <View style={styles.container}>
      <MediaCardCarousel
        data={[
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
          {
            placeHolderImage:
              'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
            mediaUrl:
              'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4',
          },
        ]}
        style={{
          width: '100%',
          height: 400,
        }}
      />
      {/* <MediaCardView
        loopCount={0}
        playerProps={{
          mediaUrl: 'https://media1.giphy.com/media/Ju7l5y9osyymQ/giphy.gif',
        }}
        style={styles.box}
      />
      <MediaCardView
        loopCount={0}
        playerProps={{
          mediaUrl: 'https://i.blogs.es/6f44dd/google-2015-1/1366_2000.jpg',
        }}
        style={styles.box}
      /> */}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 300,
    height: 200,
    marginVertical: 20,
  },
});
