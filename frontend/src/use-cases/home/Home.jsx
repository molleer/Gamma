import React from "react";

import {
    DigitLayout,
    useGammaIsAdmin,
    useGammaUser
} from "@cthit/react-digit-components";

import UserOptions from "./elements/user-options";
import AdminOptions from "./elements/admin-options";
import WelcomeUser from "./elements/welcome-user";

const Home = () => {
    const admin = useGammaIsAdmin();
    const user = useGammaUser();

    if (user == null) {
        return null;
    }

    console.log(user);

    return (
        <DigitLayout.Center>
            <DigitLayout.Column>
                <WelcomeUser user={user} />
                <DigitLayout.Spacing />
                <UserOptions
                    hasGroups={
                        user.relationships != null &&
                        user.relationships.length > 0
                    }
                />
                {admin && (
                    <>
                        <DigitLayout.Spacing />
                        <AdminOptions />
                    </>
                )}
            </DigitLayout.Column>
        </DigitLayout.Center>
    );
};

export default Home;
