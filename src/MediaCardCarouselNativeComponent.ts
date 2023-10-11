import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';

interface NativeProps extends ViewProps {
  playerProps?: {
    mediaUrl?: string;
    placeHolderImage?: string;
  };
  text?: string;
  loopCount?: number;
}

export default codegenNativeComponent<NativeProps>('MediaCardCarousel');
