// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <UIKit/UIKit.h>

#ifndef MediaCardViewNativeComponent_h
#define MediaCardViewNativeComponent_h

NS_ASSUME_NONNULL_BEGIN

@interface MediaCardView : RCTViewComponentView
@end

NS_ASSUME_NONNULL_END

#endif /* MediaCardViewNativeComponent_h */
#endif /* RCT_NEW_ARCH_ENABLED */
