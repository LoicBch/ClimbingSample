//
//  PartnersScreen.swift
//  iosApp
//
//  Created by USER on 19/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI


struct PartnersScreen: View {
    
    @StateObject var viewModel: PartnersViewModel = PartnersViewModel()
    
    public var body: some View {
                VerticalPartnersList(partners: viewModel.partners)
    }
}

struct VerticalPartnersList: View {
    
    var partners: [Partner]
    
    var body: some View{
        ScrollView(){
            LazyVStack{
                ForEach(partners , id: \.self) { partner in
                    NavigationLink(destination: PartnersDetailsScreen(partner: partner)
                        .navigationBarBackButtonHidden(true)
                    ) {
                        VerticalPartnerListItem(partner: partner)
                            .frame(width: .infinity, height: 130)
                            .padding(8)
                            .background(RoundedRectangle(cornerRadius: 5)
                                .fill(Color.white).shadow(color: Color.black.opacity(0.2), radius: 2, x: 0, y: 2))
                    }
                }
            }.padding(.top, 30).padding(.horizontal, 8)
        }.background(
            GeometryReader { geo -> Color in
                return Color("LightGrey")
            }
        ).ignoresSafeArea()
        .frame(width: .infinity, height: .infinity)
    }
}


struct VerticalPartnerListItem: View {
    
    var partner: Partner
    
    var body: some View{
        
        HStack(){
            if (!partner.photos.isEmpty) {
                ZStack(){
                    Image("").frame(width: 130, height: 130)
                }
            }
            
            VStack(){
                Text(partner.name)
                    .fontWeight(.medium)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .font(.custom("CircularStd-Medium", size: 14))
                    .foregroundColor(.black)
                    .padding(.top, 8)
                    .padding(.leading, 8)
                
                Text(partner.description_)
                    .fontWeight(.medium)
                    .multilineTextAlignment(.leading)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .font(.custom("CircularStd-Medium", size: 10))
                    .foregroundColor(Color("Neutral50"))
                    .padding(.top, 5)
                    .padding(.leading, 8)
                
                Spacer()
                
                HStack(){
                    Image("shield")
                        .frame(width: 16, height: 16)
                        .padding(5)
                        .background(Color.white)
                        .cornerRadius(5)
                        .shadow(color: Color.black.opacity(0.2), radius: 2, x: 0, y: 2)
                    
                    
                    Image("premium_badge")
                        .frame(width: 16, height: 16)
                        .padding(5)
                        .background(Color.white)
                        .cornerRadius(5)
                        .shadow(color: Color.black.opacity(0.2), radius: 2, x: 0, y: 2)
                    
                }.padding(8)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
        }
    }
}
