//
//  SortDealers.swift
//  iosApp
//
//  Created by USER on 09/05/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared


struct SortingSheet: View {
    
    @State var sortingOptionSelected = "none"
    
    var updateSource: UpdateSource
    var onSortingApplied: (SortingOption) -> Void
    var onClose: () -> Void
    
    
    var body: some View{
        VStack{
            HStack{
                Image(systemName: "xmark").onTapGesture {
                    onClose()
                }
                Spacer()
                Text("Sorting")
                Spacer()
            }.padding(.top, 12).padding(.horizontal, 16)
            
            HStack{
                LocalizedText(key: "sort_places")
                //                    .fontWeight(.black)
                    .font(.system(size: 14))
                    .foregroundColor(Color.black)
                Spacer()
            }.padding(.top, 35).padding(.horizontal, 16)
            Divider().padding(.horizontal, 16)
            
            SortingRadioGroup(items: updateSource.getFilterOption(), selectedId: $sortingOptionSelected) { selected in
                sortingOptionSelected = selected
                let option = SortingOption(rawValue: selected)
            }.padding(.horizontal, 16).onAppear{
                print(updateSource)
                print(updateSource.getFilterOption())
            }
            Spacer()
            AppButton(action: { onSortingApplied(SortingOption(rawValue: sortingOptionSelected)!); onClose() }, title: "apply_sorting", isEnable: true).padding(.bottom, 16)
        }
    }
} 

enum SortingOption: String {
    case none = "none"
    case byDistFromMe = "by_distance_from_you"
    case byDistFromLastSearch = "distance_from_searched_location"
    case byDate = "by_date"
}

extension UpdateSource {
    func getFilterOption() -> [SortingOption] {
        if (self == UpdateSource.aroundMe) {
            return [SortingOption.none, SortingOption.byDistFromMe]
        } else if (self == UpdateSource.aroundPlace){
            return [SortingOption.none, SortingOption.byDistFromMe, SortingOption.byDistFromLastSearch]
        } else if (self == UpdateSource.aroundPosition){
            return [SortingOption.none, SortingOption.byDistFromMe]
        } else if (self == UpdateSource.events){
            return [SortingOption.none, SortingOption.byDate, SortingOption.byDistFromMe]
        }else{
            return []
        }
    }
}

extension SortingOption: CaseIterable {
    func getLabel() -> String {
        switch self {
        case SortingOption.byDate: return "by_date"
        case SortingOption.byDistFromLastSearch: return "distance_from_searched_location"
        case SortingOption.byDistFromMe: return "by_distance_from_you"
        case SortingOption.none: return "none"
        default: return "";
        }
    }
    
    func getIcon() -> String {
        switch self {
        case SortingOption.byDate: return "events"
        case SortingOption.byDistFromLastSearch: return "distance"
        case SortingOption.byDistFromMe: return "distance"
        case SortingOption.none: return "xmark.circle"
        default: return "";
        }
    }
    
    func getSortDomain() -> SortOption {
        switch self {
        case SortingOption.byDate: return SortOption.byDate
        case SortingOption.byDistFromLastSearch: return SortOption.distFromSearched
        case SortingOption.byDistFromMe: return SortOption.distFromYou
        case SortingOption.none: return SortOption.none
        default: return SortOption.none;
        }
    }
}


