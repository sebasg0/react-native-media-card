import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';

interface NativeProps extends ViewProps {
  mediaUrl?: string;
  text?: string;
  loopCount?: number;
}

export default codegenNativeComponent<NativeProps>('MediaCardView');
