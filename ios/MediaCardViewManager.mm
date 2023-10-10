#import <React/RCTUIManager.h>
#import "RCTBridge.h"
#import "MediaCardViewManager.h"
#import "MediaCardView.h"

@implementation MediaCardViewManager

RCT_EXPORT_MODULE(MediaCardView)

- (UIView *)view {
    return [[MediaCardView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(mediaUrl, NSString)
RCT_EXPORT_VIEW_PROPERTY(loopCount, NSInteger)

@end
