import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';

interface PlayerProps {
  mediaUrl?: string;
  placeHolderImage?: string;
}

interface NativeProps extends ViewProps {
  data: PlayerProps[];
}

export default codegenNativeComponent<NativeProps>('MediaCardCarousel');
